package np.minarybook.util

class StringUtil {

    fun stringToList(input: String): List<String> {
        // 문자열에서 대괄호를 제거하고 요소를 콤마로 분리하여 리스트로 변환
        return input.removeSurrounding("[", "]")
            .split(", ")
            .map { it.trim() }
            .filter { it.isNotEmpty() }
    }
}