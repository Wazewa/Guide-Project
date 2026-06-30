import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Router, RouterModule } from '@angular/router';
import { MatToolbarModule } from '@angular/material/toolbar';
import { MatButtonModule } from '@angular/material/button';
import { AuthService, UserDto } from './services/auth.service';
import { NotificationService } from './services/notification.service';

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [
    CommonModule,
    RouterModule,
    MatToolbarModule,
    MatButtonModule
  ],
  templateUrl: './app.component.html',
  styleUrl: './app.component.scss'
})
export class AppComponent implements OnInit {
  user: UserDto | null = null;

  constructor(
    private authService: AuthService,
    private router: Router,
    private notificationService: NotificationService  // ← добавить
  ) {}

  ngOnInit() {
    this.authService.currentUser$.subscribe({
      next: (data) => {
        this.user = data;
        if (data) {
          console.log('✅ Пользователь загружен:', data);
        } else {
          console.log('❌ Пользователь не залогинен');
        }
      },
      error: (err) => console.error('Ошибка подписки:', err)
    });

    this.authService.loadCurrentUser();
  }

  logout(): void {
    this.notificationService.confirm('Вы уверены, что хотите выйти?').subscribe({
      next: (confirmed) => {
        if (confirmed) {
          this.authService.logout().subscribe({
            next: () => {
              this.user = null;
              this.router.navigate(['/']);
            },
            error: (err) => {
              console.error('Ошибка выхода:', err);
            }
          });
        }
      }
    });
  }
}
