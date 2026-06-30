import { Component, Inject } from '@angular/core';
import { CommonModule } from '@angular/common';
import { MAT_SNACK_BAR_DATA, MatSnackBarRef } from '@angular/material/snack-bar';
import { MatButtonModule } from '@angular/material/button';

@Component({
  selector: 'app-confirm-dialog',
  standalone: true,
  imports: [CommonModule, MatButtonModule],
  template: `
    <div class="confirm-container">
      <span class="message">{{ data.message }}</span>
      <div class="actions">
        <button mat-button class="btn-cancel" (click)="dismiss(false)">Нет</button>
        <button mat-button class="btn-confirm" (click)="dismiss(true)">Да</button>
      </div>
    </div>
  `,
  styles: [`
    .confirm-container {
      display: flex;
      align-items: center;
      justify-content: space-between;
      padding: 16px 24px;
      border-radius: 12px;
      min-width: 320px;
      background: #212121;
      color: #ffffff;
      border: 2px solid #ffc107;
      box-shadow: 0 8px 24px rgba(0,0,0,0.6);
    }
    .message {
      font-weight: 500;
      font-size: 15px;
      margin-right: 20px;
    }
    .actions {
      display: flex;
      gap: 8px;
    }
    .btn-cancel {
      color: #999 !important;
    }
    .btn-cancel:hover {
      background: rgba(255,255,255,0.05) !important;
    }
    .btn-confirm {
      color: #ffc107 !important;
      font-weight: 600;
    }
    .btn-confirm:hover {
      background: rgba(255,193,7,0.1) !important;
    }
  `]
})
export class ConfirmDialogComponent {
  constructor(
    @Inject(MAT_SNACK_BAR_DATA) public data: { message: string },
    private snackBarRef: MatSnackBarRef<ConfirmDialogComponent>
  ) {}

  dismiss(result: boolean): void {
    if (result) {
      this.snackBarRef.dismissWithAction();
    } else {
      this.snackBarRef.dismiss();
    }
  }
}

