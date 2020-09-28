package element_helper;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.apache.log4j.Logger;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.lang.reflect.Type;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

public enum StoreHelper {

    INSTANCE;
    private Logger log = Logger.getLogger(StoreHelper.class);
    private static final String defaultDirectoryPath = "elementValues";
    ConcurrentHashMap<String, Object> elementMapList;

    StoreHelper(){
        initMap(getFileList());
    }

    private void initMap(File[] fileList){

        elementMapList = new ConcurrentHashMap<>();
        Type elementType = new TypeToken<List<ElementInfo>>(){
        }.getType();
        Gson gson = new Gson();
        List<ElementInfo> elementInfoList = null;
        for(File file : fileList){
            try {
                elementInfoList = gson
                        .fromJson(new FileReader(file), elementType);
                elementInfoList.parallelStream()
                        .forEach(elementInfo -> elementMapList.put(elementInfo.getKey(), elementInfo));

            } catch (FileNotFoundException e){
                log.error(String.format("'%s Dosya Bulunamadı!", e));
            }
        }
    }
    private File[] getFileList(){
        URI uri = null;
        try {
            uri = new URI(this.getClass().getClassLoader().getResource(defaultDirectoryPath).getFile());
        }catch (URISyntaxException e){
            log.error(String.format("'%s Dosya yolu Bulunamadı!", defaultDirectoryPath));
            throw new NullPointerException();
        }
        File[] fileList = new File(uri.getPath())
                .listFiles(pathname -> !pathname.isDirectory() && pathname.getName().endsWith(".json"));

        return fileList;
    }

    public ElementInfo findElementByInfo (String key){
        return (ElementInfo) elementMapList.get(key);
    }

}
