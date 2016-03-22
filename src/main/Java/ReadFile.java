import org.bson.Document;

import java.io.*;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class ReadFile {

    private static int eachLine = 100;
    private static int topLine = 116416;
    private static int bundary = 80;

    private static String INPATH = "src/main/resource/hapmap.gz";
    private static String OUTPATH = "src/main/resource/out.xlsx";
    private LinkedList<String> unitList;


    public void process() throws IOException {
        File file = new File(INPATH);
        InputStreamReader read = new InputStreamReader(new FileInputStream(file),"UTF-8");
        BufferedReader reader = new BufferedReader(read);
        int lineNumber = 0;
        String line;
        MongoDBDriver driver = new MongoDBDriver();
        while ((line = reader.readLine()) != null) {
            if (lineNumber == 0) {
                unitList = (LinkedList<String>)splitByTable(line);
                lineNumber++;
                continue;
            }
            List<String> list = translateLineToArray(line);
            Document object = createObject(list,lineNumber);
            driver.insertData(object);
            lineNumber++;
            System.out.println(lineNumber);
        }
        read.close();
        reader.close();
    }

    public List<String> translateLineToArray(String line){
        List<String> list;
        if(line.contains("\t")) {
            list = splitByTable(line);
        }else {
            list = splitBySpace(line);
        }
        return list;
    }

    public List<String> splitByTable(String line){
        String[] array = line.split("\t");
        LinkedList<String> outputList = new LinkedList<String>();
        for (String cell: array) {
            if (cell.contains(" ")){
                String[] cellArray = cell.split(" ");
                outputList.add(cellArray[0]);
                outputList.add(cellArray[1]);
            }else {
                outputList.add(cell);
            }
        }
        return outputList;
    }

    public List<String> splitBySpace(String line){
        String[] array = line.split(" ");
        List<String> outputList = Arrays.asList(array);
        return outputList;
    }

    public Document createObject(List<String> dataList, int line){
        Document object = new Document();
        object.append("line_number",line);
        for (int i = 0; i < dataList.size(); i++) {
            object.append(unitList.get(i),dataList.get(i));
        }
        return object;
    }
}
