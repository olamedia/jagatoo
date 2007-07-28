package org.jagatoo.loaders.models.collada.jibx;

import java.util.ArrayList;
import java.util.StringTokenizer;

import org.jagatoo.util.math.MatrixUtils;
import org.openmali.vecmath.Matrix4f;
import org.openmali.vecmath.Point3f;

/**
 * A Node can have a Transform and can instance
 * geometries/controllers (for skeletal animation).
 * Child of VisualScene or Node.
 * 
 * @author Amos Wenger (aka BlueSky)
 */
public class XMLNode {
    
    public ArrayList<String> layers = null;
    public String sid = null;
    public static enum Type {
        NODE,
        JOINT
    }
    public Type type = null;
    public String id = null;
    public String name = null;
    
    public XMLMatrix4x4 matrix = new XMLMatrix4x4();
    public ArrayList<XMLInstanceGeometry> instanceGeometries = null;
    public ArrayList<XMLInstanceController> instanceControllers = null;
    
    public ArrayList<XMLNode> childrenList = null;
    
    //private String translateString;
    //private String rotateString;
    //private String scaleString;
    
    public static ArrayList<String> parseLayerList(String str) {
        ArrayList<String> layers = new ArrayList<String>();
        StringTokenizer tknz = new StringTokenizer(str);
        while(tknz.hasMoreTokens()) {
            layers.add(tknz.nextToken());
        }
        return layers;
    }
    
    public void applyTranslate(String str) {
        StringTokenizer tknz = new StringTokenizer(str);
        Point3f translate = new Point3f(
                Float.parseFloat(tknz.nextToken()),
                Float.parseFloat(tknz.nextToken()),
                Float.parseFloat(tknz.nextToken())
        );
        Matrix4f mat = new Matrix4f();
        mat.setIdentity();
        mat.setTranslation(translate);
        // System.out.println("Mat before translate : \n"+matrix.matrix4f);
        matrix.matrix4f.mul(mat);
        // System.out.println("Mat after translate of "+translate+" : \n"+matrix.matrix4f);
    }
    
    public void applyRotate(String str) {
        StringTokenizer tknz = new StringTokenizer(str);
        Point3f rotate = new Point3f(
                Float.parseFloat(tknz.nextToken()),
                Float.parseFloat(tknz.nextToken()),
                Float.parseFloat(tknz.nextToken())
        );
        float angle = Float.parseFloat(tknz.nextToken());
        rotate.x *= angle;
        rotate.y *= angle;
        rotate.z *= angle;
        Matrix4f mat = MatrixUtils.eulerToMatrix4f(rotate);
        // System.out.println("Mat before rotate : \n"+matrix.matrix4f);
        matrix.matrix4f.mul(mat);
        // System.out.println("Mat after rotate of "+rotate+" : \n"+matrix.matrix4f);
    }
    
    public void applyScale(String str) {
        StringTokenizer tknz = new StringTokenizer(str);
        
        Point3f scale = new Point3f(
                Float.parseFloat(tknz.nextToken()),
                Float.parseFloat(tknz.nextToken()),
                Float.parseFloat(tknz.nextToken())
        );
        
        // System.out.println("Mat before scale : \n"+matrix.matrix4f);
        
        matrix.matrix4f.m00 *= scale.x;
        matrix.matrix4f.m01 *= scale.x;
        matrix.matrix4f.m02 *= scale.x;
        matrix.matrix4f.m03 *= scale.x;
        
        matrix.matrix4f.m10 *= scale.y;
        matrix.matrix4f.m11 *= scale.y;
        matrix.matrix4f.m12 *= scale.y;
        matrix.matrix4f.m13 *= scale.y;
        
        matrix.matrix4f.m20 *= scale.z;
        matrix.matrix4f.m21 *= scale.z;
        matrix.matrix4f.m22 *= scale.z;
        matrix.matrix4f.m23 *= scale.z;
        
        // System.out.println("Mat after scale of "+scale+" : \n"+matrix.matrix4f);
    }
    
}
