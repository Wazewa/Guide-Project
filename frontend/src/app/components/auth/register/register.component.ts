import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Router, RouterModule } from '@angular/router';
import { MatCardModule } from '@angular/material/card';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatButtonModule } from '@angular/material/button';
import { AuthService } from '../../../services/auth.service';

@Component({
  selector: 'app-register',
  standalone: true,
  imports: [
    CommonModule,
    FormsModule,
    RouterModule,
    MatCardModule,
    MatFormFieldModule,
    MatInputModule,
    MatButtonModule
  ],
  template: `
    <div class="register-container">
      <mat-card>
        <mat-card-header>
          <mat-card-title>Регистрация</mat-card-title>
        </mat-card-header>
        <mat-card-content>
          <form #registerForm="ngForm" (ngSubmit)="onSubmit()">
            <mat-form-field appearance="fill" class="full-width">
              <mat-label>Имя</mat-label>
              <input matInput type="text" [(ngModel)]="name" name="name" required>
            </mat-form-field>

            <mat-form-field appearance="fill" class="full-width">
              <mat-label>Фамилия</mat-label>
              <input matInput type="text" [(ngModel)]="surname" name="surname" required>
            </mat-form-field>

            <mat-form-field appearance="fill" class="full-width">
              <mat-label>Email</mat-label>
              <input matInput type="email" [(ngModel)]="email" name="email" required>
            </mat-form-field>

            <mat-form-field appearance="fill" class="full-width">
              <mat-label>Пароль</mat-label>
              <input matInput type="password" [(ngModel)]="password" name="password" required>
            </mat-form-field>

            <button mat-raised-button color="primary" type="submit" [disabled]="registerForm.invalid">
              Зарегистрироваться
            </button>
            <button mat-button type="button" routerLink="/login">Уже есть аккаунт? Войти</button>
          </form>
          <p *ngIf="error" class="error">{{ error }}</p>
          <p *ngIf="success" class="success">{{ success }}</p>
        </mat-card-content>
      </mat-card>
    </div>
  `,
  styles: [`
    .register-container { max-width: 400px; margin: 50px auto; padding: 0 16px; }
    .full-width { width: 100%; margin-bottom: 16px; }
    .error { color: red; margin-top: 12px; }
    .success { color: green; margin-top: 12px; }
  `]
})
export class RegisterComponent {
  name: string = '';
  surname: string = '';
  email: string = '';
  password: string = '';
  error: string = '';
  success: string = '';

  constructor(private authService: AuthService, private router: Router) {
  }

  onSubmit() {
    const data = {
      name: this.name,
      surname: this.surname,
      email: this.email,
      password: this.password
    };

    this.authService.register(data).subscribe({
      next: () => {
        this.authService.login({email: this.email, password: this.password}).subscribe({
          next: () => {
            this.router.navigate(['/']);
          },
          error: () => {
            this.router.navigate(['/login']);
          }
        });
      },
      error: (err: any) => {
        this.error = err.error?.message || 'Ошибка регистрации';
        this.success = '';
        console.error(err);
      }
    });
  }
}
