package apk.dispatcher.channel.honor

import apk.dispatcher.ApkChannelTask
import apk.dispatcher.util.defaultLogger
import java.io.File
import kotlin.math.roundToInt

class HonorChannelTask : ApkChannelTask() {

    override val channelName: String = "荣耀"

    override val fileNameIdentify: String = "HONOR"

    override val paramDefine: List<Param> = listOf(
        CLIENT_ID,
        CLIENT_SECRET,
    )
    private var clientId = ""

    private var clientSecret = ""

    private val connectClient = HonorConnectClient()

    override fun init(params: Map<Param, String?>) {
        defaultLogger.info("参数:$params")
        clientId = params[CLIENT_ID] ?: ""
        clientSecret = params[CLIENT_SECRET] ?: ""
    }

    override suspend fun performUpload(file: File, updateDesc: String, progress: (Int) -> Unit) {
        connectClient.uploadApk(file, clientId, clientSecret, updateDesc) {
            progress((it * 100).roundToInt())
        }
    }
    companion object {
        private val CLIENT_ID = Param("client_id", desc = "客户端ID")
        private val CLIENT_SECRET = Param("client_secret", desc = "秘钥")
    }

}