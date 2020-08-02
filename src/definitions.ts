// add this line to ignore tsc errors to fix the issue
// @ts-ignore
declare module '@capacitor/core' {
  interface PluginRegistry {
    NotificationDownloadProgress: NotificationDownloadProgress;
  }
}

export interface createOptions {
  contentTitle?: string
  contentText?: string
  destroyOnDone?: boolean
}

export interface setProgressOptions {
  progress: number
  doneTitle?: string
  doneText?: string
}

export interface NotificationDownloadProgress {
  /**
   * Create notification
   * @param options {createOptions}
   * @returns {Promise<any>}
   */
  create(options: createOptions): Promise<any>;
  /**
   * Update progressbar value
   * @param options {setProgressOptions}
   * @returns {Promise<any>}
   */
  setProgress(options: setProgressOptions): Promise<any>;
}
