import type { CapacitorConfig } from '@capacitor/cli';

const config: CapacitorConfig = {
  appId: 'com.meetqlingo.mobile',
  appName: 'MeetqLingo',
  webDir: 'build',
  server: {
    hostname: 'localhost',
    //androidScheme: 'http'
    androidScheme: 'https'
  }
};

export default config;
