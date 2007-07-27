package org.jagatoo.loaders.models.collada;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

import org.jagatoo.loaders.models.collada.datastructs.COLLADAFile;
import org.jagatoo.loaders.models.collada.datastructs.effects.COLLADAEffect;
import org.jagatoo.loaders.models.collada.datastructs.effects.COLLADALibraryEffects;
import org.jagatoo.loaders.models.collada.datastructs.effects.COLLADAProfile;
import org.jagatoo.loaders.models.collada.datastructs.effects.COLLADAProfileCommon;
import org.jagatoo.loaders.models.collada.datastructs.images.COLLADASurface;
import org.jagatoo.loaders.models.collada.jibx.XMLEffect;
import org.jagatoo.loaders.models.collada.jibx.XMLLibraryEffects;
import org.jagatoo.loaders.models.collada.jibx.XMLProfileCOMMON;
import org.jagatoo.loaders.models.collada.jibx.XMLProfileCOMMON_NewParam;
import org.jagatoo.loaders.models.collada.jibx.XMLSampler2D;
import org.jagatoo.loaders.models.collada.jibx.XMLSurface;

/**
 * Loader for LibraryEffects
 *
 * @author Amos Wenger (aka BlueSky)
 */
public class COLLADALibraryEffectsLoader {

    /**
     * Load LibraryEffects
     *
     * @param colladaFile
     *            The collada file to add them to
     * @param libEffects
     *            The JAXB data to load from
     */
    static void loadLibraryEffects(COLLADAFile colladaFile,
            XMLLibraryEffects libEffects) {

        COLLADALibraryEffects colLibEffects = colladaFile.getLibraryEffects();
        Collection<XMLEffect> effects = libEffects.effects;

        for (XMLEffect effect : effects) {

            COLLADALoader.logger.print("TT] Effect \"" + effect.id + "\"");
            COLLADALoader.logger.increaseTabbing();
            COLLADAEffect colladaEffect = new COLLADAEffect(effect.id);
            colLibEffects.getEffects().put(colladaEffect.getId(), colladaEffect);
            colladaEffect.profiles = new ArrayList<COLLADAProfile>();

            if (effect.profileCOMMON != null) {
                COLLADALoader.logger.print("TT] Profile COMMON : loading...");
                COLLADALoader.logger.increaseTabbing();
                colladaEffect.profiles.add(COLLADALibraryEffectsLoader.loadCommonProfile(effect, effect.profileCOMMON));
                COLLADALoader.logger.decreaseTabbing();
            }
            if (effect.profileCG != null) {
                COLLADALoader.logger
                .print("EE] CG shaders profile isn't implemented yet !");
            }
            if (effect.profileGLSL != null) {
                COLLADALoader.logger
                .print("EE] GLSL shaders profile isn't implemented yet !");
            }
            COLLADALoader.logger.decreaseTabbing();

        }

    }

    /**
     * Loads a profile common
     * @param effect
     * @param profile
     * @param type
     * @return the loaded profile
     */
    static COLLADAProfile loadCommonProfile(XMLEffect effect, XMLProfileCOMMON profile) {

        COLLADAProfileCommon colladaProfile = new COLLADAProfileCommon();
        colladaProfile.surfaces = new HashMap<String, COLLADASurface>();

        List<XMLProfileCOMMON_NewParam> newParams = profile.newParams;

        if(newParams != null) {

            for (XMLProfileCOMMON_NewParam newParam : newParams) {

                if (newParam.surface != null) {

                    XMLSurface surface = newParam.surface;
                    COLLADASurface colladaSurface = new COLLADASurface(newParam.sid);
                    colladaSurface.imageIds = new ArrayList<String>();
                    COLLADALoader.logger.print("TT] Found surface ! (id = " + newParam.sid + ")");
                    COLLADALoader.logger.increaseTabbing();

                    String imageId = surface.initFrom;
                    COLLADALoader.logger.print("TT] Image id : " + imageId);
                    colladaSurface.imageIds.add(imageId);
                    colladaProfile.surfaces.put(colladaSurface.getId(), colladaSurface);
                    COLLADALoader.logger.decreaseTabbing();

                } else if (newParam.sampler2D != null) {

                    COLLADALoader.logger.print("TT] Found sampler ! (id = "+ newParam.sid + ")");
                    COLLADALoader.logger.increaseTabbing();
                    XMLSampler2D sampler2D = newParam.sampler2D;
                    COLLADALoader.logger.print("TT] Sampler using source : " + sampler2D.source);
                    COLLADALoader.logger.decreaseTabbing();

                }

            }

        }

        return colladaProfile;

    }

}
