package com.example.submissionuserstoryapp1.util

import androidx.recyclerview.widget.DiffUtil
import com.example.submissionuserstoryapp1.data.remote.response.ListStoryItem

class DiffUtilHelper(private val oldNoteList: ArrayList<ListStoryItem>, private val newNoteList: ArrayList<ListStoryItem>) : DiffUtil.Callback() {
    override fun getOldListSize(): Int = oldNoteList.size
    override fun getNewListSize(): Int = newNoteList.size
    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldNoteList[oldItemPosition].id == newNoteList[newItemPosition].id
    }
    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldNote = oldNoteList[oldItemPosition]
        val newNote = newNoteList[newItemPosition]
        return oldNote.name == newNote.name && oldNote.description == newNote.description && oldNote.photoUrl == newNote.photoUrl
    }
}