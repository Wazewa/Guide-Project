import { Component, OnInit, OnDestroy } from '@angular/core';
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
export class AppComponent implements OnInit, OnDestroy {
  user: UserDto | null = null;

  // ✅ Массив с картинками для фона
  backgroundImages: string[] = [
    '/assets/1.jpg',
    '/assets/2.jpg',
    '/assets/3.jpg',
    '/assets/4.jpg',
    '/assets/5.jpg'
  ];

  currentBackground: string = this.backgroundImages[0];
  private intervalId: any;

  constructor(
    private authService: AuthService,
    private router: Router,
    private notificationService: NotificationService
  ) {}

  ngOnInit() {
    this.authService.currentUser$.subscribe({
      next: (data) => {
        this.user = data;
        if (data) {
          console.log('Пользователь загружен:', data);
        } else {
          console.log('Пользователь не залогинен');
        }
      },
      error: (err) => console.error('Ошибка подписки:', err)
    });

    this.authService.loadCurrentUser();

    let index = 0;
    this.intervalId = setInterval(() => {
      index = (index + 1) % this.backgroundImages.length;
      this.currentBackground = this.backgroundImages[index];
    }, 5000);
  }

  ngOnDestroy() {
    if (this.intervalId) {
      clearInterval(this.intervalId);
    }
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
