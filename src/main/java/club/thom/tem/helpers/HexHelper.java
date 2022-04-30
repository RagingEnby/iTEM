package club.thom.tem.helpers;

import club.thom.tem.TEM;
import club.thom.tem.constants.*;
import com.google.common.base.Strings;

import static club.thom.tem.models.inventory.item.ArmourPieceData.convertIntArrayToHex;

public class HexHelper {
    public static String convertSmallerHex(String smallerHex) {
        if (smallerHex.length() == 1) {
            return Strings.repeat(smallerHex, 6);
        } else if (smallerHex.length() == 3) {
            char[] characters = smallerHex.toCharArray();
            return Strings.repeat(Character.toString(characters[0]), 2) +
                    Strings.repeat(Character.toString(characters[1]), 2) +
                    Strings.repeat(Character.toString(characters[2]), 2);
        } else {
            // Assume it's a 6 digit hex.
            return smallerHex;
        }
    }

    public static boolean checkOriginal(String itemId, String hexCode) {
        int[] colours = TEM.items.getDefaultColour(itemId);
        String originalHex = convertIntArrayToHex(colours);
        if (itemId.startsWith("GREAT_SPOOK")) {
            return SpookColours.isSpookColour(hexCode);
        }
        if (VariantColours.isVariantColour(itemId, hexCode)) {
            return true;
        }
        return hexCode.equalsIgnoreCase(originalHex);
    }

    public enum Modifier {
        CRYSTAL,
        FAIRY,
        OG_FAIRY,
        UNDYED,
        ORIGINAL,
        EXOTIC,
    }

    public static Modifier getModifier(String itemId, String hexCode) {
        if (checkOriginal(itemId, hexCode)) {
            return Modifier.ORIGINAL;
        } else if (FairyColours.isFairyColour(hexCode)) {
            if (FairyColours.isOGFairyColour(itemId, hexCode)) {
                return Modifier.OG_FAIRY;
            }
            return Modifier.FAIRY;
        } else if (hexCode.equals("A06540") || hexCode.equals("UNDYED")) {
            return Modifier.UNDYED;
        } else if (CrystalColours.isCrystalColour(hexCode)) {
            return Modifier.CRYSTAL;
        } else {
            return Modifier.EXOTIC;
        }
    }
}
