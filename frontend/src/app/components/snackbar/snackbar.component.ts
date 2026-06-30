import { Component, Inject } from '@angular/core';
import { CommonModule } from '@angular/common';
import { MAT_SNACK_BAR_DATA, MatSnackBarRef } from '@angular/material/snack-bar';
import { MatButtonModule } from '@angular/material/button';

@Component({
  selector: 'app-snackbar',
  standalone: true,
  imports: [CommonModule, MatButtonModule],
  template: `
    <div class="snackbar-container" [class.success]="data.type === 'success'" [class.error]="data.type === 'error'">
      <span class="message">{{ data.message }}</span>
      <button mat-button class="close-btn" (click)="dismiss()">✕</button>
    </div>
  `,
  styles: [`
    .snackbar-container {
      display: flex;
      align-items: center;
      justify-content: space-between;
      padding: 12px 20px;
      border-radius: 12px;
      min-width: 300px;
      background: #212121;
      color: #ffffff;
      border: 2px solid #ffc107;
      box-shadow: 0 8px 24px rgba(0,0,0,0.6);
    }
    .snackbar-container.success {
      border-color: #4caf50;
    }
    .snackbar-container.error {
      border-color: #ff5252;
    }
    .icon {
      font-size: 24px;
      margin-right: 12px;
    }
    .message {
      flex: 1;
      font-weight: 500;
      font-size: 14px;
    }
    .close-btn {
      color: #ffc107 !important;
      font-weight: 600;
      min-width: 32px;
      padding: 0;
      margin-left: 12px;
    }
  `]
})
export class SnackbarComponent {
  constructor(
    @Inject(MAT_SNACK_BAR_DATA) public data: { message: string; type: 'success' | 'error' },
    private snackBarRef: MatSnackBarRef<SnackbarComponent>
  ) {}

  dismiss(): void {
    this.snackBarRef.dismiss();
  }
}
