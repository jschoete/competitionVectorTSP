package competition;

import java.io.File;
import java.io.IOException;

public class FileCreator {
	
	public static void createFile(String fileName) throws IOException {
		File file = new File(fileName);
		file.getParentFile().mkdirs();
		file.createNewFile();
	}
}
