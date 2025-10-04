package com.example.a7minuteworkout

object Constants {
    fun defaultExerciseList(): ArrayList<ExerciseModel>{
        val exerciseList = ArrayList<ExerciseModel>()
        val jumpingJacks = ExerciseModel(
           1,
            "Jumping Jacks",
             R.drawable.jumpingjacks,
             false,
            false
        )
        exerciseList.add(jumpingJacks)
        val pushUps = ExerciseModel(
            2,
            "Push-Ups",
            R.drawable.pushups,
            false,
            false
        )
        exerciseList.add(pushUps)
        val diamondPushUps = ExerciseModel(
            3,
            "Diamond Push-Ups",
            R.drawable.diamondpushups,
            false,
            false
        )
        exerciseList.add(diamondPushUps)
        val burpees = ExerciseModel(
            4,
            "Burpees",
            R.drawable.burpees,
            false,
            false
        )
        exerciseList.add(burpees)
        val squats = ExerciseModel(
            5,
            "Squats",
            R.drawable.squats,
            false,
            false
        )
        exerciseList.add(squats)
        val lunges = ExerciseModel(
            6,
            "Lunges",
            R.drawable.lunges,
            false,
            false
        )
        exerciseList.add(lunges)
        val calfRaises = ExerciseModel(
            7,
            "Calf Raises",
            R.drawable.calfraises,
            false,
            false
        )
        exerciseList.add(calfRaises)
        val wallSit = ExerciseModel(
            8,
            "WallSits",
            R.drawable.wallsit,
            false,
            false
        )
        exerciseList.add(wallSit)
        val tricepDip = ExerciseModel(
            9,
            "TricepDips",
            R.drawable.tricepdips,
            false,
            false
        )
        exerciseList.add(tricepDip)
        val crunches = ExerciseModel(
            10,
            "Crunches",
            R.drawable.crunches,
            false,
            false
        )
        exerciseList.add(crunches)
        val legRaises = ExerciseModel(
            11,
            "Leg Raises",
            R.drawable.legraises,
            false,
            false
        )
        exerciseList.add(legRaises)
        val russianTwist = ExerciseModel(
            12,
            "Russian Twists",
            R.drawable.russiantwist,
            false,
            false
        )
        exerciseList.add(russianTwist)
        val plank = ExerciseModel(
            13,
            "Plank",
            R.drawable.plank,
            false,
            false
        )
        exerciseList.add(plank)

        return exerciseList
    }
}