package com.disfinder.hobot;

import static spark.Spark.get;

public class Main implements spark.servlet.SparkApplication{

	public static void main(String[] args) {
		get("/hello", (req, res) -> "Hello World");

	}

	@Override
	public void init() {
		// TODO Auto-generated method stub
		get("/hello", (req, res) -> "Hello World");
//		get("/", (req, res) -> "Root page");
		get("/", (req, res) ->
			{
				return SelfDocumentation.generateHTML();
				
			});
		
	}

}
