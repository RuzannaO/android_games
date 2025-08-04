import type { CapacitorConfig } from '@capacitor/cli';

const config: CapacitorConfig = {
  appId: 'com.meetqonline.armquiz',
  appName: 'ArmQuiz',
  webDir: '.',
  server: {
    url: 'https://meetqonline.com/arm-edu?from_app=1',
    androidScheme: 'https',
    cleartext: false
  }
};

export default config;