# Simple Diaries Frontend

Frontend application for the Simple Diaries Application built with Next.js 15 and React 19.

## Technology Stack

- **Next.js**: 15.x
- **React**: 19.x
- **TypeScript**: 5.x
- **Tailwind CSS**: 4.x
- **ESLint**: Code quality

## Project Structure

```
frontend/
├── src/
│   └── app/           # Next.js App Router pages
│       ├── layout.tsx # Root layout
│       ├── page.tsx   # Home page
│       └── globals.css
├── public/            # Static assets
├── package.json
├── tsconfig.json
└── next.config.ts
```

## Prerequisites

- Node.js 18+ or Bun
- npm, yarn, or bun

## Getting Started

### Install Dependencies

```bash
npm install
# or
bun install
```

### Run Development Server

```bash
npm run dev
# or
bun dev
```

Open [http://localhost:3000](http://localhost:3000) in your browser.

### Build for Production

```bash
npm run build
npm start
# or
bun run build
bun start
```

## Available Scripts

- `dev` - Start development server
- `build` - Build for production
- `start` - Start production server
- `lint` - Run ESLint

## API Integration

The frontend will connect to the backend API at `http://localhost:8080/api`

Configure the API URL in environment variables:

```env
NEXT_PUBLIC_API_URL=http://localhost:8080/api
```

## Features

- User authentication (login/register)
- Diary entry management (create, read, update, delete)
- Responsive design
- Client-side routing with Next.js App Router
