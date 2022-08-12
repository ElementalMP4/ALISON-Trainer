package main.java.de.voidtech.alisontrainer;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import main.java.de.voidtech.alison.entities.AlisonWord;

public class AlisonTrainer {
	
	private static final Logger LOGGER = Logger.getLogger(AlisonTrainer.class.getSimpleName());
	
	private static List<AlisonWord> tokenise(String content) {
        List<String> tokens = Arrays.asList(content.split(" "));
        List<AlisonWord> newWords = new ArrayList<AlisonWord>();
        for (int i = 0; i < tokens.size(); ++i) {
            if (i == tokens.size() - 1) newWords.add(new AlisonWord(tokens.get(i), "StopWord"));
            else newWords.add(new AlisonWord(tokens.get(i), tokens.get(i + 1)));
        }
        return newWords;
	}
	
	private static void save(List<AlisonWord> words, String name) {
		try {
			String dataDir = "output/" + name;
			File dataFile = new File(dataDir);
			if (!dataFile.exists()) dataFile.mkdirs();
			FileOutputStream fileOutStream = new FileOutputStream(dataDir + "/words.alison");
			ObjectOutputStream objectOutStream = new ObjectOutputStream(fileOutStream);
			objectOutStream.writeObject(words);
			objectOutStream.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		if (args.length == 0) {
			LOGGER.severe("You need to specify an input file!");
			return;
		}
		File inputFile = new File(args[0]);
		if (!inputFile.exists()) {
			LOGGER.severe("That file could not be found!");
			return;
		}
		List<String> result;
		try (Stream<String> lines = Files.lines(Paths.get(inputFile.getPath()))) {
			result = lines.collect(Collectors.toList());
		} catch (IOException e) {
			LOGGER.severe(e.getMessage());
			return;
		}
		LOGGER.info("Tokenising " + result.size() + " lines...");
		List<AlisonWord> finalWords = result.stream().map(AlisonTrainer::tokenise).flatMap(List::stream).collect(Collectors.toList());
		LOGGER.info("Tokenisation complete. Saving to model...");
		save(finalWords, inputFile.getName());
		LOGGER.info("Model saved!");
	}
}