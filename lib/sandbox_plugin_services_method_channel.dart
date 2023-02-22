import 'package:flutter/foundation.dart';
import 'package:flutter/services.dart';

import 'sandbox_plugin_services_platform_interface.dart';

/// An implementation of [SandboxPluginServicesPlatform] that uses method channels.
class MethodChannelSandboxPluginServices extends SandboxPluginServicesPlatform {
  /// The method channel used to interact with the native platform.
  @visibleForTesting
  final methodChannel = const MethodChannel('sandbox_plugin_services');

  @override
  Future<String?> getPlatformVersion() async {
    final version = await methodChannel.invokeMethod<String>('getPlatformVersion');
    return version;
  }

  @override
  Future<String?> getBtn1() async {
    final res = await methodChannel.invokeMethod<String>('getBtn1');
    return res;
  }

  @override
  Future<String?> getBtn2() async {
    final res = await methodChannel.invokeMethod<String>('getBtn2');
    return res;
  }
}
