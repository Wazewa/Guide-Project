import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Router, RouterModule } from '@angular/router';
import { MatToolbarModule } from '@angular/material/toolbar';
import { MatButtonModule } from '@angular/material/button';
import { AuthService, UserDto } from './services/auth.service';

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
    private router: Router
  ) {}

  ngOnInit() {
    // ✅ Подписываемся на изменения пользователя
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

  logout() {
    this.authService.logout().subscribe({
      next: () => {
        console.log('✅ Выход выполнен');
        this.router.navigate(['/']);
      },
      error: (err) => {
        console.error('❌ Ошибка выхода:', err);
        this.router.navigate(['/']);
      }
    });
  }
}
