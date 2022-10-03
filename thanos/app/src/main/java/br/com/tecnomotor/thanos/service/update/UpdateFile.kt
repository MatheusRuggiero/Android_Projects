package br.com.tecnomotor.thanos.service.update

class UpdateFile(
    val fileName: String?,
    val directory: String?,
    val md5: String?,
    val type: UpdateFileType,
    val priority: Int
) {
    override fun toString(): String {
        // TODO Auto-generated method stub
        return "$fileName($type)"
    }
}