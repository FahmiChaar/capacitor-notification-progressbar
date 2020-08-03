// add this line to ignore tsc errors to fix the issue
// @ts-ignore
declare module '@capacitor/core' {
  interface PluginRegistry {
    NotificationDownloadProgress: NotificationDownloadProgress;
  }
}

export interface CreateNotificationOptions {
  contentTitle?: string
  contentText?: string
  destroyOnDone?: boolean
}

export interface UpdateProgressOptions {
  progress: number
  doneTitle?: string
  doneText?: string
}

export interface NotificationDownloadProgress {
  /**
   * Create notification
   * @param options {CreateNotificationOptions}
   * @returns {Promise<any>}
   */
  create(options: CreateNotificationOptions): Promise<any>;
  /**
   * Update progressbar value
   * @param options {UpdateProgressOptions}
   * @returns {Promise<any>}
   */
  updateProgress(options: UpdateProgressOptions): Promise<any>;
  /**
   * Destroy the notification
   */
  destroy(): void
}
