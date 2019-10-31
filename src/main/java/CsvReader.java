import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class CsvReader {


    public static List<BankCsvModel> getBankModelList(String filePath) {
        List<BankCsvModel> modelList = new ArrayList<>();
        try (BufferedReader br = Files.newBufferedReader(Paths.get(filePath), StandardCharsets.US_ASCII)) {
            br.readLine();
            String line;
            line = br.readLine();
            while (line != null) {
                line=line.replace("\"","");
                String[] attributes = line.split(";");
                BankCsvModel model = new BankCsvModel(attributes);
                modelList.add(model);
                line = br.readLine();
            }
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
        return modelList;
    }
}
