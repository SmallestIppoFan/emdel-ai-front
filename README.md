# Инструкция по подключению к бэкенду

## Быстрый старт

### 1. Клонирование репозитория бэкенда

```bash
git clone https://github.com/amirslanbek02/emdel-ai-python-backend.git
cd emdel-ai-python-backend
```

### 2. Настройка окружения

**Создайте виртуальное окружение:**

```bash
# Windows
python -m venv venv
venv\Scripts\activate

# Linux/Mac
python3 -m venv venv
source venv/bin/activate
```

**Установите зависимости:**

```bash
pip install -r requirements.txt
```

### 3. Настройка переменных окружения

**Создайте файл `.env` в корне проекта бэкенда:**

```env
# OpenAI API (обязательно)
OPENAI_API_KEY=sk-your-actual-api-key-here

# Опционально
OPENAI_MODEL=gpt-4
MAX_HISTORY_MESSAGES=10

# 2GIS API (опционально, для поиска клиник)
TWO_GIS_KEY=your-2gis-api-key
DEFAULT_CITY=Алматы
```

**Где получить API ключи:**
- OpenAI: https://platform.openai.com/api-keys
- 2GIS: https://dev.2gis.com/

### 4. Запуск бэкенда

```bash
# Через uvicorn
uvicorn app.main:app --reload --host 0.0.0.0 --port 8000

# Или через Python модуль
python -m app.main
```

Сервер будет доступен по адресу: `http://localhost:8000`

### 5. Настройка Android приложения

В файле `app/src/main/java/com/example/emdel/data/config/ApiConfig.kt`:

```kotlin
object ApiConfig {
    // Для эмулятора Android
    const val BASE_URL = "http://10.0.2.2:8000"
    
    // Для реального устройства - замените на IP вашего компьютера
    // const val BASE_URL = "http://192.168.1.X:8000"
}
```

### 6. Проверка подключения

1. Убедитесь, что бэкенд запущен и доступен
2. Откройте в браузере: http://localhost:8000/docs (Swagger UI)
3. Запустите Android приложение
4. Отправьте тестовое сообщение в чат

## Структура API

### Основные endpoints:

- `GET /` - Проверка работы API
- `GET /health` - Health check
- `POST /api/chat` - Отправить сообщение
- `GET /api/chat/history` - Получить историю чата

### Пример запроса:

```json
POST /api/chat
{
  "text": "У меня болит голова",
  "user_id": "user123"  // опционально
}
```

### Пример ответа:

```json
{
  "id": "550e8400-e29b-41d4-a716-446655440000",
  "text": "Понимаю. Расскажите подробнее о симптомах...",
  "isFromUser": false,
  "timestamp": 1704067200000,
  "clinics": [
    {
      "name": "Клиника Здоровье",
      "address": "г. Алматы, ул. Абая, д. 10",
      "rating": 4.7,
      "whatsapp": "+77001234567",
      "working_hours": "Пн-Пт: 09:00-18:00"
    }
  ]
}
```

## Устранение проблем

### Ошибка подключения из Android:

1. **Эмулятор:** Используйте `http://10.0.2.2:8000`
2. **Реальное устройство:** 
   - Узнайте IP вашего компьютера: `ipconfig` (Windows) или `ifconfig` (Linux/Mac)
   - Используйте `http://YOUR_IP:8000`
   - Убедитесь, что устройство и компьютер в одной сети Wi-Fi

### Ошибка "OPENAI_API_KEY не найден":

- Проверьте, что файл `.env` создан в корне проекта бэкенда
- Убедитесь, что ключ указан правильно (без пробелов, кавычек)
- Перезапустите сервер после изменения `.env`

### CORS ошибки:

Бэкенд уже настроен для работы с мобильным приложением. Если возникают проблемы, проверьте настройки CORS в `app/main.py`.

## Дополнительная информация

- Репозиторий бэкенда: https://github.com/SmallestIppoFan/emdel-ai-python-backend
- README бэкенда содержит подробную информацию о структуре проекта


