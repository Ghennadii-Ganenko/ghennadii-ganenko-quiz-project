package com.ghennadiiganenko.android.ghennadiiganenko_quiz_project.data

data class SportQuizQuestions(
    val questions: List<String> = listOf(
        "How long is a marathon?",
        "How many players are there on a baseball team",
        "Which country won the World Cup 2018?",
        "What sport is considered the “king of sports”?",
        "What team won the first NBA game in 1946?",
        "What is the oldest water sport ever recorded?",
        "Which swimming style is not allowed in the Olympics?",
        "Which of the following is not a water sport?",
        "Which country has the most Olympic gold medals in swimming?",
        "Of all the fighting sports below, which sport wasn’t practised by Bruce Lee?",
    ),

    val answers: List<String> = listOf(
        "42.195 kilometres",
        "9 players",
        "France",
        "Soccer",
        "The New York Knicks",
        "Diving",
        "Dog paddle",
        "Paragliding",
        "The USA",
        "Wushu",
    ),

    val choices: List<List<String>> = listOf(
        listOf("41.335 kilometres", "42.195 kilometres", "45.523 kilometres", "50.746 kilometres"),
        listOf("9 players", "5 players", "7 players", "11 players"),
        listOf("France", "Germany", "Sweden", "Italy"),
        listOf("Baseball", "Football", "Soccer", "Ice hockey"),
        listOf("St. Louis Bombers", "Boston Celtics", "Washington Capitols", "The New York Knicks"),
        listOf("Waterskiing", "Surfing", "Diving", "Kayaking"),
        listOf("Dog paddle", "Freestyle", "Backstroke", "Butterfly"),
        listOf("Paragliding", "Cliff diving", "Windsurfing", "Rowing"),
        listOf("China", "The USA", "The UK", "Australia"),
        listOf("Wushu", "Boxing", "Jeet Kune Do", "Fencing"),
    )
)
