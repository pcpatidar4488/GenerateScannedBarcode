package generatebarcode.com.generatebarcode.utils;

/**
 * Created by manjoor on 03-Oct-18.
 */

public class ClassString {
    public static String CODE128 = "Code 128 barcodes are compact, high-density codes used in logistics and transportation industries for ordering and distribution. They’re geared toward non-POS products, like when supply chain applications label units with serial shipping container codes (SSCC). Because they support any character of the ASCII 128 character set, Code 128 barcodes can store highly diversified information." +
            "\n" +
            "Industry: Supply Chain";

    public static String CODE39 = "Code39 barcodes (or Code 3 of 9) are used to label goods across many industries and are often found in the automotive industry and the U.S. Department of Defense. It enables the use of both digits and characters, and its name originates in the fact that it could only encode 39 characters—though in its most recent version the character set has been increased to 43. It’s similar to, but not as compact as, the Code 128 barcode.\n" +
            "\n" +
            "Industry: Automotive and Defense\n";
    public static String CODE93 = "Code 93 barcodes are used in logistics to identify packages in retail inventory, label electronic components, and even provide supplementary delivery information for the Canadian Post. Like Code 39, Code 93 barcodes come with full ASCII support. Along with enabling additional security within the code itself, the high density and compact size of Code 93s make them around 25% shorter than barcodes produced in Code 39.\n" +
            "\n" +
            "Industry: Retail, Manufacturing and Logistics\n";
    public static String EAN13 = "EAN barcodes are also used to label consumer goods worldwide for point-of-sale scanning, primarily in Europe. They look very similar to UPC codes, and the main distinction is their geographical application. While EAN-13 (comprising 13 digits) is the default form factor, you’ll find EAN-8 (covering eight digits) barcodes on products where space is very limited, like small candies.\n" +
            "\n" +
            "Industry: Retail\n" +
            "Variations: EAN-13, EAN-8, JAN-13, ISBN, ISSN";
    public static String EAN8 = "EAN barcodes are also used to label consumer goods worldwide for point-of-sale scanning, primarily in Europe. They look very similar to UPC codes, and the main distinction is their geographical application. While EAN-13 (comprising 13 digits) is the default form factor, you’ll find EAN-8 (covering eight digits) barcodes on products where space is very limited, like small candies.\n" +
            "\n" +
            "Industry: Retail\n" +
            "Variations: EAN-13, EAN-8, JAN-13, ISBN, ISSN";
    public static String CODABAR = "Codabar barcodes are used by logistics and healthcare professionals, including U.S. blood banks, FedEx, photo labs and libraries. Their main benefit is that they’re easy to print and can be produced by any impact style printer—even a typewriter. Therefore, users can create many Codabar codes using consecutive numbers without the use of a computer. Codabar is a discrete, self-checking symbology that encodes up to 16 different characters with an additional 4 start/stop characters.\n" +
            "\n" +
            "Variations: Codeabar, Ames Code, NW-7, Monarch, Code 2 of 7, Rationalized Codabar, ANSI/AIM BC3-1995, USD-";
    public static String UPCE = "UPC barcodes are used to label and scan consumer goods at points of sale around the world—mainly in the United States, but also in the United Kingdom, Australia, New Zealand and other countries. The UPC-A variation encodes twelve numerical digits, while UPC-E is a smaller variation that encodes only six numerical digits.\n" +
            "\n" +
            "Industry: Retail\n" +
            "Variations: UPC-A, UPC-E\n";
    public static String UPCA = "UPC barcodes are used to label and scan consumer goods at points of sale around the world—mainly in the United States, but also in the United Kingdom, Australia, New Zealand and other countries. The UPC-A variation encodes twelve numerical digits, while UPC-E is a smaller variation that encodes only six numerical digits.\n" +
            "\n" +
            "Industry: Retail\n" +
            "Variations: UPC-A, UPC-E\n";
    public static String ITF = "ITF (or Interleaved 2 of 5) barcodes are used to label packaging materials across the globe. Since they can deal with high printing tolerances, they are ideal for printing on corrugated cardboard. ITF barcodes encode 14 numeric digits and use the full ASCII set.\n" +
            "\n" +
            "Industry: Packaging\n";
    public static String QR_CODE = "QR codes are most often used in tracking and marketing initiatives, such as advertisements, magazines and business cards. They are flexible in size, offer a high fault tolerance and have fast readability, though they can’t be read with a laser scanner. QR codes support four different modes of data: numeric, alphanumeric, byte/binary, and Kanji. They are public domain and free to use.\n"+
            "\n"+
            "Industry: Retail, Entertainment and Advertising\n";
    public static String PDF417 = "PDF417 codes are used for applications that require the storage of huge amounts of data, such as photographs, fingerprints and signatures. They can hold over 1.1 kilobytes of machine-readable data, making them much more powerful than other 2D barcodes. Like QR codes, PDF417 barcodes are public domain and free to use.\n" +
            "\n" +
            "Variation: Truncated PDF417\n" +
            "Industry: Logistics and Government\n";
    public static String DATA_MATRIX = "Datamatrix codes are usually used to label small items, goods and documents. Their tiny footprint makes them ideal for small products in logistics and operations. In fact, the U.S. Electronic Industries Alliance (EIA) recommends that they be used to label small electronic components. Similar to QR codes, they have high fault tolerance and fast readability.\n"+
            "\n"+
            "Variation: Micro-Datamatrix\n"+
            "Industry: Electronics, Retail and Government\n";
    public static String AZTEC = "Aztec codes are commonly used by the transportation industry, particularly for tickets and airline boarding passes. The barcodes can still be decoded even if they have bad resolution, making them useful when tickets are printed poorly or presented on a phone. They can also take up less space because they don’t require a surrounding blank “quiet zone,” unlike some other 2D barcode types.\n" +
            "\n" +
            "Industry: Transportation\n";
}
