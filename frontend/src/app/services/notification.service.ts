import { Injectable } from '@angular/core';
import { MatSnackBar } from '@angular/material/snack-bar';
import { SnackbarComponent } from '../components/snackbar/snackbar.component';

@Injectable({
  providedIn: 'root'
})
export class NotificationService {
  constructor(private snackBar: MatSnackBar) {}

  success(message: string): void {
    this.snackBar.openFromComponent(SnackbarComponent, {
      data: { message, type: 'success' },
      duration: 3000,
      horizontalPosition: 'center',
      verticalPosition: 'top',
      panelClass: 'custom-snackbar'
    });
  }

  error(message: string): void {
    this.snackBar.openFromComponent(SnackbarComponent, {
      data: { message, type: 'error' },
      duration: 4000,
      horizontalPosition: 'center',
      verticalPosition: 'top',
      panelClass: 'custom-snackbar'
    });
  }
}
