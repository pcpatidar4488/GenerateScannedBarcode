package generatebarcode.com.generatebarcode.utils;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Build;
import android.support.v4.content.ContextCompat;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static android.graphics.Color.BLACK;
import static android.graphics.Color.WHITE;

/**
 * Created by manjoor on 27-Sep-18.
 */

public class Helpers {
    public static List barcodeList() {
        List<Map> list = new ArrayList<>();
        Map map;
        map = new HashMap();
        map.put("code_name", "EAN-8");
        map.put("code_des", "7 digit + 1 checksum [0-9]");
        map.put("code_type", "" + IBarcode.EAN8);
        map.put("code_info", "" + ClassString.EAN8);
        list.add(map);
        map = new HashMap();
        map.put("code_name", "EAN-13");
        map.put("code_des", "12 digit + 1 checksum [0-9]");
        map.put("code_type", "" + IBarcode.EAN13);
        map.put("code_info", "" + ClassString.EAN13);
        list.add(map);
        map = new HashMap();
        map.put("code_name", "UPC-A");
        map.put("code_des", "11 digit [0-9]");
        map.put("code_type", "" + IBarcode.UPCA);
        map.put("code_info", "" + ClassString.UPCA);
        list.add(map);
        //2D
        map = new HashMap();
        map.put("code_name", "QR-CODE");
        map.put("code_des", "Up to 1K UTF-8 characters");
        map.put("code_type", "" + IBarcode.QR_CODE);
        map.put("code_info", "" + ClassString.QR_CODE);
        list.add(map);
        map = new HashMap();
        map.put("code_name", "CODE-39");
        map.put("code_des", "[A-Z]; [0-9]; [- . $ / + %]");
        map.put("code_type", "" + IBarcode.CODE39);
        map.put("code_info", "" + ClassString.CODE39);
        list.add(map);
        map = new HashMap();
        map.put("code_name", "CODE-128");
        map.put("code_des", "Max 80 ASCII character");
        map.put("code_type", "" + IBarcode.CODE128);
        map.put("code_info", "" + ClassString.CODE128);
        list.add(map);
        map = new HashMap();
        map.put("code_name", "ITF");
        map.put("code_des", "13 digit + 1 checksum[0-9]");
        map.put("code_type", "" + IBarcode.ITF);
        map.put("code_info", "" + ClassString.ITF);
        list.add(map);
        map = new HashMap();
        map.put("code_name", "PDF-417");
        map.put("code_des", "Up to 1K ASCII characters");
        map.put("code_type", "" + IBarcode.PDF417);
        map.put("code_info", "" + ClassString.PDF417);
        list.add(map);
        map = new HashMap();
        map.put("code_name", "CODABAR");
        map.put("code_des", "[0-9]; [- $ : / . +]");
        map.put("code_type", "" + IBarcode.CODABAR);
        map.put("code_info", "" + ClassString.CODABAR);
        list.add(map);
        map = new HashMap();
        map.put("code_name", "DATA-MATRIX");
        map.put("code_des", "Up to 1K ASCII characters");
        map.put("code_type", "" + IBarcode.DATA_MATRIX);
        map.put("code_info", "" + ClassString.DATA_MATRIX);
        list.add(map);
         map = new HashMap();
        map.put("code_name", "AZTEC");
        map.put("code_des", "Up to 1 UTF-8 pr ISO 8859-1 characters");
        map.put("code_type", "" + IBarcode.AZTEC);
        map.put("code_info", "" + ClassString.AZTEC);
        list.add(map);
       /* map = new HashMap();
        map.put("code_name", "CODE-93");
        map.put("code_des", "[A-Z]; [0-9]; space; [- . $ / + %]");
        map.put("code_type", "" + IBarcode.CODE93);
        map.put("code_info", "" + ClassString.CODE93);
        list.add(map);
        map = new HashMap();
        map.put("code_name", "UPC-E");
        map.put("code_des", "8 digit [0-9]");
        map.put("code_type", "" + IBarcode.UPCE);
        map.put("code_info", "" + ClassString.UPCE);
        list.add(map);*/
        return list;
    }

    public static List<String> barcodeStringList() {
        List<String> list = new ArrayList<>();
        list.add("Select Barcode Type");
        list.add("CODE-128 (code_des,Max 80 ASCII character)");
        list.add("CODE-39 ([A-Z]; [0-9]; [- . $ / + %])");
        list.add("CODE-93 ([A-Z]; [0-9]; [_ - . $ / + %])");
        list.add("EAN-13 (12 digit + 1 checksum [0-9])");
        list.add("EAN-8 (7 digit + 1 checksum [0-9])");
        list.add("CODABAR ([0-9]; [- $ : / . +])");
        list.add("UPC-E (8 digit [0-9])");
        list.add("UPC-A (12 digit [0-9])");
        list.add("ITF (13 digit + 1 checksum[0-9])");
        return list;
    }

    public static Bitmap encodeAsBitmap(String contents, BarcodeFormat format, int img_width, int img_height) throws WriterException {
        String contentsToEncode = contents;
        if (contentsToEncode == null) {
            return null;
        }
        Map<EncodeHintType, Object> hints = null;
        String encoding = guessAppropriateEncoding(contentsToEncode);
        if (encoding != null) {
            hints = new EnumMap<EncodeHintType, Object>(EncodeHintType.class);
            hints.put(EncodeHintType.CHARACTER_SET, encoding);
        }
        MultiFormatWriter writer = new MultiFormatWriter();
        BitMatrix result;
        try {
            result = writer.encode(contentsToEncode, format, img_width, img_height, hints);
        } catch (IllegalArgumentException iae) {
            // Unsupported format
            return null;
        }
        int width = result.getWidth();
        int height = result.getHeight();
        int[] pixels = new int[width * height];
        for (int y = 0; y < height; y++) {
            int offset = y * width;
            for (int x = 0; x < width; x++) {
                pixels[offset + x] = result.get(x, y) ? BLACK : WHITE;
            }
        }

        Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        bitmap.setPixels(pixels, 0, width, 0, 0, width, height);
        return bitmap;
    }

    private static String guessAppropriateEncoding(CharSequence contents) {
        // Very crude at the moment
        for (int i = 0; i < contents.length(); i++) {
            if (contents.charAt(i) > 0xFF) {
                return "UTF-8";
            }
        }
        return null;
    }

    private static final String ALPHA_NUMERIC_STRING = "abcdefghijklmnopqrstuvwxyz0123456789";

    public static String randomAlphaNumeric(int count) {
        StringBuilder builder = new StringBuilder();
        while (count-- != 0) {
            int character = (int) (Math.random() * ALPHA_NUMERIC_STRING.length());
            builder.append(ALPHA_NUMERIC_STRING.charAt(character));
        }
        return builder.toString();
    }

    private static final String ALPHA_UPPER_NUMERIC_STRING = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789-.$/+% ";

    public static String randomAlphaNumericUpper(int count) {
        StringBuilder builder = new StringBuilder();
        while (count-- != 0) {
            int character = (int) (Math.random() * ALPHA_UPPER_NUMERIC_STRING.length());
            builder.append(ALPHA_UPPER_NUMERIC_STRING.charAt(character));
        }
        return builder.toString();
    }

    private static final String NUMERIC = "0123456789";

    public static String randomNumeric(int count) {
        StringBuilder builder = new StringBuilder();
        while (count-- != 0) {
            int character = (int) (Math.random() * NUMERIC.length());
            builder.append(NUMERIC.charAt(character));
        }
        return builder.toString();
    }

    public static Bitmap barcodeBitmap(int code_type, String input_data,int width,int height) {
        Bitmap bitmap = null;
        if (code_type == IBarcode.CODE128) {
            try {
                bitmap = Helpers.encodeAsBitmap(input_data, BarcodeFormat.CODE_128, width, height);
            } catch (WriterException e) {
                e.printStackTrace();
                System.out.println("ppppppppp  "+e.getMessage());
            }
        } else if (code_type == IBarcode.CODE39) {
            try {
                bitmap = Helpers.encodeAsBitmap(input_data, BarcodeFormat.CODE_39, width, height);
            } catch (WriterException e) {
                e.printStackTrace();
            }
        } else if (code_type == IBarcode.CODE93) {
            try {
                bitmap = Helpers.encodeAsBitmap(input_data, BarcodeFormat.CODE_93, width, height);
            } catch (WriterException e) {
                e.printStackTrace();
            }
        } else if (code_type == IBarcode.EAN13) {
            try {
                input_data = getEAN13CheckSumString(input_data);
                bitmap = Helpers.encodeAsBitmap(input_data, BarcodeFormat.EAN_13, width, height);
            } catch (WriterException e) {
                e.printStackTrace();
            }
        } else if (code_type == IBarcode.EAN8) {
            try {
                bitmap = Helpers.encodeAsBitmap(input_data, BarcodeFormat.EAN_8, width, height);
            } catch (WriterException e) {
                e.printStackTrace();
            }
        } else if (code_type == IBarcode.CODABAR) {
            try {
                bitmap = Helpers.encodeAsBitmap(input_data, BarcodeFormat.CODABAR, width, height);
            } catch (WriterException e) {
                e.printStackTrace();
            }
        } else if (code_type == IBarcode.UPCE) {
            try {
                bitmap = Helpers.encodeAsBitmap(input_data, BarcodeFormat.UPC_E, width, height);
            } catch (WriterException e) {
                e.printStackTrace();
            }
        } else if (code_type == IBarcode.UPCA) {
            try {
               // footer = getUPCACheckSumString(footer);
                bitmap = Helpers.encodeAsBitmap(input_data, BarcodeFormat.UPC_A, width, height);
            } catch (WriterException e) {
                e.printStackTrace();
            }
        } else if (code_type == IBarcode.ITF) {
            try {
                bitmap = Helpers.encodeAsBitmap(input_data, BarcodeFormat.ITF, width, height);
            } catch (WriterException e) {
                e.printStackTrace();
            }
        } else if (code_type == IBarcode.QR_CODE) {
            try {
                bitmap = Helpers.encodeAsBitmap(input_data, BarcodeFormat.QR_CODE, width, height);
            } catch (WriterException e) {
                e.printStackTrace();
            }
        }else if (code_type == IBarcode.PDF417) {
            try {
                bitmap = Helpers.encodeAsBitmap(input_data, BarcodeFormat.PDF_417, width, height);
            } catch (WriterException e) {
                e.printStackTrace();
            }
        }else if (code_type == IBarcode.DATA_MATRIX) {
            try {
                bitmap = Helpers.encodeAsBitmap(input_data, BarcodeFormat.DATA_MATRIX, width, height);
            } catch (WriterException e) {
                e.printStackTrace();
            }
        }else if (code_type == IBarcode.AZTEC) {
            try {
                bitmap = Helpers.encodeAsBitmap(input_data, BarcodeFormat.AZTEC, width, height);
            } catch (WriterException e) {
                e.printStackTrace();
            }
        }
        return bitmap;
    }

    public static String barcodeRandomString(int code_type) {
        String input_data = "";
        if (code_type == IBarcode.CODE128) {
            input_data = randomAlphaNumeric(12);
        } else if (code_type == IBarcode.CODE39) {
            input_data = randomAlphaNumericUpper(12);
        } else if (code_type == IBarcode.CODE93) {
            input_data = randomAlphaNumericUpper(12);
        } else if (code_type == IBarcode.EAN13) {
            input_data =randomNumeric(12);
        } else if (code_type == IBarcode.EAN8) {
            input_data = randomNumeric(8);
        } else if (code_type == IBarcode.CODABAR) {
            input_data = randomNumeric(12);
        } else if (code_type == IBarcode.UPCE) {
            input_data = randomNumeric(8);
        } else if (code_type == IBarcode.UPCA) {
            input_data = randomNumeric(11);
        } else if (code_type == IBarcode.ITF) {
            input_data = randomNumeric(14);
        }else if (code_type == IBarcode.QR_CODE) {
            input_data = randomAlphaNumeric(12);
        }else if (code_type == IBarcode.PDF417) {
            input_data = randomAlphaNumeric(15);
        }else if (code_type == IBarcode.DATA_MATRIX) {
            input_data = randomAlphaNumeric(80);
        }else if (code_type == IBarcode.AZTEC) {
            input_data = randomAlphaNumeric(256);
        }
        return input_data;
    }

    public static String getEAN13CheckSumString(String codeStringValue) {
        int chetVal = 0, nechetVal = 0;
        String codeToParse = codeStringValue;

        for( int index = 0;index<6;index++ )
        {
            chetVal += Integer.valueOf(codeToParse.substring(
                    index*2+1,index*2+2)).intValue();
            nechetVal += Integer.valueOf(codeToParse.substring(
                    index*2,index*2+1)).intValue();
        }

        chetVal *= 3;
        int controlNumber = 10 - (chetVal+nechetVal)%10;
        if( controlNumber == 10 ) controlNumber  = 0;

        codeToParse += String.valueOf(controlNumber);

        return codeToParse;

    }

    public static String getUPCACheckSumString(String contents) {
        int length = contents.length();
        if (length == 11) {
            // No check digit present, calculate it and add it
            int sum = 0;
            for (int i = 0; i < 11; ++i) {
                sum += (contents.charAt(i) - '0') * (i % 2 == 0 ? 3 : 1);
            }
            contents += (1000 - sum) % 10;
        } else if (length != 12) {
            throw new IllegalArgumentException(
                    "Requested contents should be 11 or 12 digits long, but got " + contents.length());
        }
        return contents;

    }

    public static String barcodeScan(int code_type, String value) {
        String bool = "";
        if (code_type == IBarcode.CODE39) {
            bool = "CODE-39 = " + value;
        } else if (code_type == IBarcode.CODE93) {
            bool = "CODE-93 = " + value;
        } else if (code_type == IBarcode.EAN13) {
            bool = "EAN-13 = " + value;
        } else if (code_type == IBarcode.EAN8) {
            bool = "EAN-8 = " + value;
        } else if (code_type == IBarcode.UPCE) {
            bool = "UPC-E = " + value;
        } else if (code_type == IBarcode.UPCA) {
            bool = "UPC-A = " + value;
        } else if (code_type == IBarcode.ITF) {
            bool = "ITF = " + value;
        } else if (code_type == IBarcode.QR_CODE) {
            bool = "QR-CODE = " + value;
        } else if (code_type == IBarcode.PDF417) {
            bool = "PDF417 = " + value;
        } else if (code_type == IBarcode.AZTEC) {
            bool = "AZTEC = " + value;
        } else if (code_type == IBarcode.DATA_MATRIX) {
            bool = "DATA-MATRIX = " + value;
        } else if (code_type == IBarcode.CODE128) {
            bool = "CODE-128 = " + value;
        } else if (code_type == IBarcode.CODABAR) {
            bool = "CODABAR = " + value;
        }
        return bool;
    }

    public static void darkenStatusBar(Activity activity, int color) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {

            activity.getWindow().setStatusBarColor(
                    darkenColor(
                            ContextCompat.getColor(activity, color)));
        }

    }

    // Code to darken the color supplied (mostly color of toolbar)
    private static int darkenColor(int color) {
        float[] hsv = new float[3];
        Color.colorToHSV(color, hsv);
        hsv[2] *= 0.8f;
        return Color.HSVToColor(hsv);
    }
}
