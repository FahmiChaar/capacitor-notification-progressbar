// add this line to ignore tsc errors to fix the issue
// @ts-ignore
declare module '@capacitor/core' {
  interface PluginRegistry {
    NotificationDownloadProgress: NotificationDownloadProgress;
  }
}

export interface NotificationDownloadProgress {
  echo(options: { value: string }): Promise<{ value: string }>;
  create(): Promise<any>;
  setProgress(progress: number): Promise<any>;
}
