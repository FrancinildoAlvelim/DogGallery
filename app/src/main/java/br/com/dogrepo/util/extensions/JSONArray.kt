package br.com.dogrepo.util.extensions

import org.json.JSONArray

fun JSONArray.forEach(itemTaken: (String) -> Unit) {
    for (i in 0 until this.length()) {
        itemTaken(this[i].toString())
    }
}
