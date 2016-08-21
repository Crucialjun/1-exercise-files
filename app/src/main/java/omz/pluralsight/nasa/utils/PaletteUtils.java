package omz.pluralsight.nasa.utils;

import android.support.v7.graphics.Palette;

/**
 * Created by omrierez on 27/02/16.
 */
public class PaletteUtils {

    public static Palette.Swatch getSwatch(Palette palette) {
        Palette.Swatch swatch = palette.getDarkVibrantSwatch();
        if (swatch==null)
            swatch=palette.getDarkMutedSwatch();
        if (swatch==null)
            swatch=palette.getLightVibrantSwatch();
        if (swatch==null)
            swatch=palette.getLightMutedSwatch();
        return swatch;
    }
}
