import 'package:flutter_test/flutter_test.dart';
import 'package:sandbox_plugin_services/sandbox_plugin_services.dart';
import 'package:sandbox_plugin_services/sandbox_plugin_services_platform_interface.dart';
import 'package:sandbox_plugin_services/sandbox_plugin_services_method_channel.dart';
import 'package:plugin_platform_interface/plugin_platform_interface.dart';

class MockSandboxPluginServicesPlatform
    with MockPlatformInterfaceMixin
    implements SandboxPluginServicesPlatform {

  @override
  Future<String?> getPlatformVersion() => Future.value('42');
}

void main() {
  final SandboxPluginServicesPlatform initialPlatform = SandboxPluginServicesPlatform.instance;

  test('$MethodChannelSandboxPluginServices is the default instance', () {
    expect(initialPlatform, isInstanceOf<MethodChannelSandboxPluginServices>());
  });

  test('getPlatformVersion', () async {
    SandboxPluginServices sandboxPluginServicesPlugin = SandboxPluginServices();
    MockSandboxPluginServicesPlatform fakePlatform = MockSandboxPluginServicesPlatform();
    SandboxPluginServicesPlatform.instance = fakePlatform;

    expect(await sandboxPluginServicesPlugin.getPlatformVersion(), '42');
  });
}
