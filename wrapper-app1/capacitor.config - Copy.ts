import type { CapacitorConfig } from '@capacitor/cli';

const config: CapacitorConfig = {
  appId: 'com.meetqlingo.mobile',
  appName: 'MeetqLingo',
  webDir: 'build',
  server: {
    url: 'http://192.168.1.8:8000/meetqlingo?from_app=1',
    cleartext: true
  }
};

export default config;
