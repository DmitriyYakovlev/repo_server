package com.example.diplom.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.hibernate.Session;

import com.example.constants.*;
import com.example.data.DbHelper;
import com.example.data.VocabulariesListJH;
import com.example.data.VocabularyT;
import com.example.data.Words;
import com.example.data.WordsListJH;

/**
 * Servlet implementation class AndroidClientServlet
 */
@WebServlet(urlPatterns = { "/AndroidClientServlet/*" })
// initParams = {
// @WebInitParam(name = "vocabulary", value = "vocabulary", description =
// "vocabulary"),
// @WebInitParam(name = "word", value = "word", description = "word")
// })
public class AndroidClientServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public AndroidClientServlet() {
		super();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		if (request.getPathInfo().length() > 0) {
			String requestType = request.getPathInfo().substring(1);
			Session session = DbHelper.openSession();

			
			if (requestType.equals(Constants.REQUEST_GET_VOCAB_LIST + "/")) {
				List<VocabularyT> allVocabularyes = DbHelper
						.getAllVocabularyes(session);

				VocabulariesListJH vocabulariesListJH = new VocabulariesListJH(
						allVocabularyes, "success");
				String jSone = vocabulariesListJH.toJson();

				sendResponse(response, jSone);
			} else if (requestType.equals(Constants.REQUEST_GET_VOCAB_BY_ID)) {
				String st1 = request.getQueryString();

				String vocabId = request.getParameter("id");
				if (vocabId != null) {
					List<Words> words = DbHelper.getWordsByVocabularyId(Integer.parseInt(vocabId), session);
					
					WordsListJH wordsListJH = new WordsListJH(words, "success");
					String wordsJsone = wordsListJH.toJson();
					
					sendResponse(response, wordsJsone);
				}
				sendResponse(response, st1);
			} else {
				sendResponse(response, "Not found request");
			}

		}

	}

	private void sendResponse(HttpServletResponse response, String message) {
		response.setContentType("text/html");
		response.setCharacterEncoding("UTF-8");
		PrintWriter output;
		try {
			output = response.getWriter();
			output.println(message);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
