# Настройка бэкенда

## Создание файла .env

1. Перейдите в директорию бэкенда: `C:\Users\amira\IdeaProjects\ai_back_service\`

2. Создайте файл `.env` в корне проекта бэкенда

3. Добавьте в файл `.env` следующее содержимое:

```
OPENAI_API_KEY=your_openai_api_key_here
```

4. Замените `your_openai_api_key_here` на ваш реальный OpenAI API ключ

## Получение OpenAI API ключа

1. Перейдите на https://platform.openai.com/api-keys
2. Войдите в свой аккаунт OpenAI
3. Нажмите "Create new secret key"
4. Скопируйте ключ (он начинается с `sk-`)
5. Вставьте ключ в файл `.env`

## Важно

- **НЕ коммитьте** файл `.env` в git (он должен быть в `.gitignore`)
- Храните ключ в безопасности
- Если ключ скомпрометирован, отзовите его на сайте OpenAI

## Пример структуры проекта бэкенда

```
ai_back_service/
├── app/
│   └── main.py
├── .env          <- Создайте этот файл
└── requirements.txt
```




