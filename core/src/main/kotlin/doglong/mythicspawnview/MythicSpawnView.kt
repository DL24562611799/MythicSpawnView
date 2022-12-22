package doglong.mythicspawnview

import doglong.mythicspawnview.core.config.RootConfig
import doglong.mythicspawnview.core.config.SpawnsConfig
import doglong.mythicspawnview.core.listener.MythicMobsListener
import doglong.mythicspawnview.core.task.MythicSpawnerUpdateTask
import doglong.mythicspawnview.libs.PluginProxy
import doglong.mythicspawnview.libs.utils.EventUtils.register
import doglong.mythicspawnview.libs.utils.StringUtils.toFormat

object MythicSpawnView : PluginProxy() {

    override fun initializing() {
        RootConfig.addActive()
        SpawnsConfig.addActive()
        MythicSpawnerUpdateTask.addActive()
    }

    override fun enabling() {
        MythicMobsListener.register()
        log("&a插件已启动完成~")
        log("&a作者: &f{0} &a| 版本: &f{1}".toFormat("DogLong", this.plugin.description.version))
    }

}