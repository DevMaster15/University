#!/usr/env python3
import telegram 
import requests

URL = 'https://api.telegram.org/bot'
BOT_TOKEN = '925752656:AAG7RpmBfQ5Z72tlNk0TORUPx00ME5zzVdo'
CHAT = '-1001444141366' 

def send_message():
    while True:
        message = input()
        result = requests.get('{}{}/sendMessage?chat_id={}&text={}'.format(URL,BOT_TOKEN, CHAT, message))
        print(result.text)

def main():
    send_message()

if __name__ == '__main__':
    main()
