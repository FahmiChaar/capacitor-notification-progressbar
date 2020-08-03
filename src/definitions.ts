// add this line to ignore tsc errors to fix the issue
// @ts-ignore
declare module '@capacitor/core' {
  interface PluginRegistry {
    NotificationDownloadProgress: NotificationDownloadProgress;
  }
}

export interface createNotificationOptions {
  contentTitle?: string
  contentText?: string
  destroyOnDone?: boolean
}

export interface updateProgressOptions {
  progress: number
  doneTitle?: string
  doneText?: string
}

export interface NotificationDownloadProgress {
  /**
   * Create notification
   * @param options {createNotificationOptions}
   * @returns {Promise<any>}
   */
  create(options: createNotificationOptions): Promise<any>;
  /**
   * Update progressbar value
   * @param options {updateProgressOptions}
   * @returns {Promise<any>}
   */
  updateProgress(options: updateProgressOptions): Promise<any>;
  /**
   * Destroy the notification
   */
  destroy(): void
}
