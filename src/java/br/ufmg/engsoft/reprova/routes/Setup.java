package br.ufmg.engsoft.reprova.routes;

import br.ufmg.engsoft.reprova.database.CourseDAO;
import br.ufmg.engsoft.reprova.routes.api.Score;
import spark.Spark;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import br.ufmg.engsoft.reprova.database.QuestionsDAO;
import br.ufmg.engsoft.reprova.routes.api.Questions;
import br.ufmg.engsoft.reprova.mime.json.Json;


/**
 * Service setup class.
 * This class is static.
 */
public class Setup {
  /**
   * Static class.
   */
  protected Setup() { }

  /**
   * Logger instance.
   */
  protected static Logger logger = LoggerFactory.getLogger(Setup.class);

  /**
   * The port for the webserver.
   */
  protected static final int port = Integer.parseInt(System.getenv("PORT"));


  /**
   * Setup the service routes.
   * This sets up the routes under the routes directory,
   * and also static files on '/public'.
   * @param json          the json formatter
   * @param questionsDAO  the DAO for Question
   * @throws IllegalArgumentException  if any parameter is null
   */
  public static void routes(Json json, QuestionsDAO questionsDAO, CourseDAO courseDAO) {
    if (json == null)
      throw new IllegalArgumentException("json mustn't be null");

    if (questionsDAO == null)
      throw new IllegalArgumentException("questionsDAO mustn't be null");

    if (courseDAO == null)
      throw new IllegalArgumentException("courseDAO mustn't be null");

    Spark.port(Setup.port);

    logger.info("Spark on port " + Setup.port);

    logger.info("Setting up static resources.");
    Spark.staticFiles.location("/public");

    logger.info("Setting up questions route:");
    Questions questions = new Questions(json, questionsDAO);
    questions.setup();

    logger.info("Setting up score route:");
    Score score = new Score(json, courseDAO);
    score.setup();
  }
}
