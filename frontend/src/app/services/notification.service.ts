import { Injectable } from '@angular/core';
import { MatSnackBar } from '@angular/material/snack-bar';
import { SnackbarComponent } from '../components/snackbar/snackbar.component';
import { ConfirmDialogComponent } from '../components/confirm-dialog/confirm-dialog.component';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class NotificationService {
  constructor(private snackBar: MatSnackBar) {
  }

  success(message: string): void {
    this.snackBar.openFromComponent(SnackbarComponent, {
      data: {message, type: 'success'},
      duration: 3000,
      horizontalPosition: 'center',
      verticalPosition: 'top',
      panelClass: 'custom-snackbar'
    });
  }

  error(message: string): void {
    this.snackBar.openFromComponent(SnackbarComponent, {
      data: {message, type: 'error'},
      duration: 4000,
      horizontalPosition: 'center',
      verticalPosition: 'top',
      panelClass: 'custom-snackbar'
    });
  }

  confirm(message: string): Observable<boolean> {
    const snackBarRef = this.snackBar.openFromComponent(ConfirmDialogComponent, {
      data: {message},
      horizontalPosition: 'center',
      verticalPosition: 'top',
      panelClass: 'custom-snackbar',
      duration: 10000
    });

    return new Observable((observer) => {
      snackBarRef.afterDismissed().subscribe(() => {
        observer.next(false);
        observer.complete();
      });

      const originalDismiss = snackBarRef.dismissWithAction.bind(snackBarRef);

      snackBarRef.dismissWithAction = () => {
        observer.next(true);
        observer.complete();
        originalDismiss();
      };
    });
  }
}
