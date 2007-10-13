package org.jagatoo.loaders.models.collada;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

import org.jagatoo.loaders.models.collada.datastructs.AssetFolder;
import org.jagatoo.loaders.models.collada.datastructs.geometries.Geometry;
import org.jagatoo.loaders.models.collada.datastructs.geometries.LibraryGeometries;
import org.jagatoo.loaders.models.collada.datastructs.geometries.Mesh;
import org.jagatoo.loaders.models.collada.datastructs.geometries.MeshDataInfo;
import org.jagatoo.loaders.models.collada.datastructs.geometries.MeshSources;
import org.jagatoo.loaders.models.collada.datastructs.geometries.TrianglesGeometry;
import org.jagatoo.loaders.models.collada.jibx.XMLGeometry;
import org.jagatoo.loaders.models.collada.jibx.XMLInput;
import org.jagatoo.loaders.models.collada.jibx.XMLLibraryGeometries;
import org.jagatoo.loaders.models.collada.jibx.XMLMesh;
import org.jagatoo.loaders.models.collada.jibx.XMLSource;
import org.jagatoo.loaders.models.collada.jibx.XMLTriangles;

/**
 * Loader for LibraryGeometries
 *
 * @author Amos Wenger (aka BlueSky)
 */
public class LibraryGeometriesLoader {

    /**
     * Load LibraryGeometries
     *
     * @param colladaFile
     *            The collada file to add them to
     * @param libGeoms
     *            The JAXB data to load from
     */
    static LibraryGeometries loadLibraryGeometries(
            AssetFolder colladaFile, XMLLibraryGeometries libGeoms) {

        // LibraryGeometries
        LibraryGeometries colladaLibGeoms = colladaFile
        .getLibraryGeometries();

        Collection<XMLGeometry> geoms = libGeoms.geometries.values();
        COLLADALoader.logger.print("There " + (geoms.size() > 1 ? "are" : "is") + " "
                + geoms.size() + " geometr" + (geoms.size() > 1 ? "ies" : "y")
                + " in this file.");

        int i = 0;
        for (XMLGeometry geom : geoms) {

            COLLADALoader.logger.print("Handling geometry " + i++);

            Geometry loadedGeom = LibraryGeometriesLoader.loadGeom(geom);
            if(loadedGeom != null) {
                colladaLibGeoms.getGeometries().put(loadedGeom.getId(), loadedGeom);
            }

        }

        return colladaLibGeoms;

    }

    /**
     * Load a specific geometry
     *
     * @param geom
     */
    @SuppressWarnings("unchecked")
    static Geometry loadGeom(XMLGeometry geom) {

        Geometry colGeom = null;

        XMLMesh mesh = geom.mesh;

        String verticesSource = mesh.vertices.inputs.get(0).source;

        // First we get all the vertices/normal data we need
        List<XMLSource> sources = mesh.sources;
        HashMap<String, XMLSource> sourcesMap = LibraryGeometriesLoader.getSourcesMap(mesh,verticesSource, sources);

        // Supported types :
        XMLTriangles tris = mesh.triangles;

        // Unsupported types :
        //Polygons polys = mesh.polygons;

        // Try triangles
        if(tris != null) {
            COLLADALoader.logger.print("TT] Primitives of type triangles");
            COLLADALoader.logger.print("TT] Polygon count = " + tris.count);

            colGeom = LibraryGeometriesLoader.loadTriangles(geom, tris, sourcesMap);
        }
        // Try polys
        /*else if(!polys.isEmpty()) {
            logger.print("TT] Primitives of type polygons");
            logger.print("TT] Polygon count = " + polys.count);

            colGeom = loadPolygons(geom, polys, sourcesMap);
        }*/
        // Well, no luck
        else {

            COLLADALoader.logger.print("EE] Can't load object : " + geom.name
                    + " because couldn't find a supported element type..." +
                    "\n (note that the only well supported type is triangles, so e.g." +
            "\n in Blender, activate the appropriate option in the export script");

        }

        return colGeom;

    }

    /**
     * Create the sources map from the information provided by JAXB
     *
     * @param mesh
     * @param verticesSource
     * @param sources
     * @return The sources map
     */
    static HashMap<String, XMLSource> getSourcesMap(XMLMesh mesh,
            String verticesSource, List<XMLSource> sources) {
        HashMap<String, XMLSource> sourcesMap;
        sourcesMap = new HashMap<String, XMLSource>();
        for (int i = 0; i < sources.size(); i++) {

            XMLSource source = sources.get(i);

            // FIXME There may be more
            if (verticesSource.equals(source.id)) {
                sourcesMap.put(mesh.vertices.id, source);
                COLLADALoader.logger.print("TT] Source " + i + " ID = "
                        + mesh.vertices.id);
            } else {
                // FIXME Are there other special cases ?
                sourcesMap.put(source.id, source);
                COLLADALoader.logger.print("TT] Source " + i + " ID = " + source.id);
            }

        }
        return sourcesMap;
    }

    /**
     * @param geom
     * @param sourcesMap
     * @param tris
     * @return the loaded geometry
     */
    @SuppressWarnings("unchecked")
    static TrianglesGeometry loadTriangles(XMLGeometry geom,
            XMLTriangles tris, HashMap<String, XMLSource> sourcesMap) {

        TrianglesGeometry trianglesGeometry = new TrianglesGeometry(
                null, geom.id, geom.name, geom);

        MeshSources sources = new MeshSources();
        MeshDataInfo meshDataInfo = LibraryGeometriesLoader.getMeshDataInfo(tris.inputs, sourcesMap, sources);

        Mesh mesh = new Mesh(sources);
        trianglesGeometry.setMesh(mesh);

        int[] indices = tris.p;
        int count = 0;
        int indexCount = indices.length / meshDataInfo.maxOffset;

        if (meshDataInfo.vertexOffset != -1) {
            mesh.vertexIndices = new int[indexCount];
        }
        if (meshDataInfo.normalOffset != -1) {
            mesh.normalIndices = new int[indexCount];
        }
        if (meshDataInfo.colorOffset != -1) {
            mesh.colorIndices = new int[indexCount];
        }
        if (meshDataInfo.uvOffsets != null) {
            mesh.uvIndices = new ArrayList<int[]>(meshDataInfo.uvOffsets.size());
            for (int i = 0; i < meshDataInfo.uvOffsets.size(); i++) {
                mesh.uvIndices.add(new int[indexCount]);
            }
        }

        /**
         * FILLING
         */
        for (int k = 0; k < indices.length; k += meshDataInfo.maxOffset) {
            if (meshDataInfo.vertexOffset != -1) {
                mesh.vertexIndices[count] = indices[k + meshDataInfo.vertexOffset];
            }
            if (meshDataInfo.normalOffset != -1) {
                mesh.normalIndices[count] = indices[k + meshDataInfo.normalOffset];
            }
            if (meshDataInfo.colorOffset != -1) {
                mesh.colorIndices[count] = indices[k + meshDataInfo.colorOffset];
            }
            if (meshDataInfo.uvOffsets != null) {
                for (int i = 0; i < meshDataInfo.uvOffsets.size(); i++) {
                    mesh.uvIndices.get(i)[count] = indices[k + meshDataInfo.uvOffsets.get(i)];
                }
            }
            count++;
        }
        /**
         * FILLING
         */

        return trianglesGeometry;

    }

    /**
     * Get the mesh data info and fill the sources
     *
     * @param inputs
     * @param sourcesMap
     *            The sources map precedently filled by
     * @param sources
     *            The sources which are going to be filled. May NOT be null.
     * @return The mesh data info
     */
    @SuppressWarnings("unchecked")
    static MeshDataInfo getMeshDataInfo(List<XMLInput> inputs, HashMap<String, XMLSource> sourcesMap,
            MeshSources sources) {

        MeshDataInfo meshDataInfo = new MeshDataInfo();

        sources.uvs = new ArrayList<float[]>();
        meshDataInfo.uvOffsets = new ArrayList<Integer>();

        COLLADALoader.logger.print("TT] Parsing semantics....");

        for (int j = 0; j < inputs.size(); j++) {
            XMLInput input = inputs.get(j);
            COLLADALoader.logger.print("TT] Input semantic " + input.semantic
                    + ", offset " + input.offset + ", from source = "
                    + input.source);

            if (input.offset > meshDataInfo.maxOffset) {
                meshDataInfo.maxOffset = input.offset;
            }

            if (input.semantic.equals("VERTEX")) {

                meshDataInfo.vertexOffset = input.offset;
                XMLSource source = sourcesMap.get(input.source);
                sources.vertices = source.floatArray.floats;


            } else if (input.semantic.equals("NORMAL")) {

                meshDataInfo.normalOffset = input.offset;
                XMLSource source = sourcesMap.get(input.source);
                sources.normals = source.floatArray.floats;

            } else if (input.semantic.equals("TEXCOORD")) {

                meshDataInfo.uvOffsets.add(input.offset);
                XMLSource source = sourcesMap.get(input.source);
                sources.uvs.add(source.floatArray.floats);

            } else if (input.semantic.equals("COLOR")) {

                meshDataInfo.colorOffset = input.offset;
                XMLSource source = sourcesMap.get(input.source);
                sources.colors = source.floatArray.floats;

            } else {

                COLLADALoader.logger.print("EE] We don't know that semantic :"
                        + input.semantic + " ! Ignoring..");

            }
        }

        // It says it needs to be max + 1
        meshDataInfo.maxOffset += 1;

        return meshDataInfo;

    }

}
