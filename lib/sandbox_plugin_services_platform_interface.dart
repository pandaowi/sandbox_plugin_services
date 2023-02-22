import 'package:plugin_platform_interface/plugin_platform_interface.dart';

import 'sandbox_plugin_services_method_channel.dart';

abstract class SandboxPluginServicesPlatform extends PlatformInterface {
  /// Constructs a SandboxPluginServicesPlatform.
  SandboxPluginServicesPlatform() : super(token: _token);

  static final Object _token = Object();

  static SandboxPluginServicesPlatform _instance = MethodChannelSandboxPluginServices();

  /// The default instance of [SandboxPluginServicesPlatform] to use.
  ///
  /// Defaults to [MethodChannelSandboxPluginServices].
  static SandboxPluginServicesPlatform get instance => _instance;

  /// Platform-specific implementations should set this with their own
  /// platform-specific class that extends [SandboxPluginServicesPlatform] when
  /// they register themselves.
  static set instance(SandboxPluginServicesPlatform instance) {
    PlatformInterface.verifyToken(instance, _token);
    _instance = instance;
  }

  Future<String?> getPlatformVersion() {
    throw UnimplementedError('platformVersion() has not been implemented.');
  }

  Future<String?> getBtn1() {
    throw UnimplementedError('platformVersion() has not been implemented.');
  }

  Future<String?> getBtn2() {
    throw UnimplementedError('platformVersion() has not been implemented.');
  }
}
