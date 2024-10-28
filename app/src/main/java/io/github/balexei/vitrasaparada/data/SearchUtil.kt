package io.github.balexei.vitrasaparada.data

fun buildSearchString(name: String, routes: String, alias: String?): String {
    val builder = StringBuilder()
    for (char in name.lowercase()) {
        builder.append(normalizationMap[char] ?: char)
    }
    alias?.let {
        for (char in it.lowercase()) {
            builder.append(normalizationMap[char] ?: char)
        }
    }
    builder.append(routes.lowercase().replace(",", ""))
    if (name.contains("Avda.")) builder.append(" avenida")
    if (name.contains("Sto.")) builder.append(" santo")
    if (name.contains("Estrada")) builder.append(" carretera")
    if (name.contains("Ceminterio")) builder.append(" cementerio")
    if (name.contains("Camiño")) builder.append(" camino")
    if (name.contains("Est.")) builder.append(" estación")
    if (name.contains("Praza")) builder.append(" plaza")
    if (name.contains("Rúa")) builder.append(" calle")
    if (name.contains("Praia")) builder.append(" playa")
    if (name.contains("Colexio")) builder.append(" colegio")
    return builder.toString()
}

fun normalizeSearch(query: String): String {
    val builder = StringBuilder(query.length)
    for (char in query.lowercase()) {
        builder.append(normalizationMap[char] ?: char)
    }
    return builder.toString()
}

private val normalizationMap = mapOf(
    'á' to 'a', 'é' to 'e', 'í' to 'i', 'ó' to 'o', 'ú' to 'u', 'ü' to 'u', "(" to "", ")" to ""
)

