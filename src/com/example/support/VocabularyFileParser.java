package com.example.support;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.hibernate.Session;
import com.example.data.DbHelper;
import com.example.data.Words;

public class VocabularyFileParser {

	public static void parseVocabFile(File tempVocabFile, int vocabId, Session session) {

		// TODO : ускладнити регул€рку.
		String reqKeyVal = "(.+)[-|=](.+)";
		Pattern pattern = Pattern.compile(reqKeyVal);

		try {
			FileReader reader = new FileReader(tempVocabFile);
			BufferedReader bufRead = new BufferedReader(reader);

			String line;
			while ((line = bufRead.readLine()) != null) {
				Matcher matcher = pattern.matcher(line);
				if (matcher.matches()) {
					String key = matcher.group(1);
					String val = matcher.group(2);

					Words curent = new Words(key, val, vocabId);
					DbHelper.addWord(curent, session);
				}
			}

			reader.close();
			tempVocabFile.delete();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
