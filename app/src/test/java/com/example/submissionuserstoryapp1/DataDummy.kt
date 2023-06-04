package com.example.submissionuserstoryapp1

import com.example.submissionuserstoryapp1.database.StoryItem

object DataDummy {
    fun generateDummyStory(): List<StoryItem> {
        val items: MutableList<StoryItem> = arrayListOf()
        for (i in 0..100) {
            val story = StoryItem(
                i.toString(),
                "photoUrl $i",
                "name $i"
            )
            items.add(story)
        }
        return items
    }
}