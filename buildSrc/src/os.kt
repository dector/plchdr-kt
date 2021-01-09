fun detectOs(): String {
    val os = System.getProperty("os.name")

    return if (os == "Mac OS X") "macos"
    else if (os.startsWith("Win")) "windows"
    else if (os.startsWith("Linux")) "linux"
    else throw IllegalStateException("Unsupported OS: '$os'")
}
