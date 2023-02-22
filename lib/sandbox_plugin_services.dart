
import 'sandbox_plugin_services_platform_interface.dart';

class SandboxPluginServices {
  Future<String?> getPlatformVersion() {
    return SandboxPluginServicesPlatform.instance.getPlatformVersion();
  }

  Future<String?> getBtn1() {
    return SandboxPluginServicesPlatform.instance.getBtn1();
  }

  Future<String?> getBtn2() {
    return SandboxPluginServicesPlatform.instance.getBtn2();
  }
}
