import ir.rayanovinmt.rnt_social_api.core.generator.XmlToCgConverter;
import ir.rayanovinmt.rnt_social_api.core.generator.CgToCodeGenerator;

public class RunGenerator {
    public static void main(String[] args) throws Exception {
        if (args.length == 0) {
            System.out.println("Usage: RunGenerator [step1|step2] [xmlFolderPath]");
            System.exit(1);
        }

        String step = args[0];
        String xmlFolderPath = args.length > 1 ? args[1] : "generate-xmls";

        if ("step1".equals(step)) {
            System.out.println("Running Step 1: XML → .cg");
            XmlToCgConverter.main(new String[]{xmlFolderPath});
        } else if ("step2".equals(step)) {
            System.out.println("Running Step 2: .cg → Java Code");
            CgToCodeGenerator.main(new String[]{xmlFolderPath});
        } else {
            System.out.println("Invalid step: " + step);
            System.exit(1);
        }
    }
}
