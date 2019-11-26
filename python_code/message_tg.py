#!/usr/env python3
import telegram 
import requests
import json

URL = 'https://api.telegram.org/bot'
BOT_TOKEN = '925752656:AAG7RpmBfQ5Z72tlNk0TORUPx00ME5zzVdo'
CHAT = '459725844' #id della chat


def send_message():
    update_id = 178335130
    
    while True: 
        result = requests.get('{}{}/getUpdates?offset={}'.format(URL, BOT_TOKEN, update_id))
        json_data = json.loads(result.text)
        
        json_result = json_data.get('result') 
        if not json_result:
            break

        for el in json_result:
            update_id = el.get('update_id')
            print(update_id)
            try: 
                print(el.get('message').get('text'))
        
            except:
                continue

            print('---------------------------------------------------------------')
            #text = el['message']['text']
            
        print(json_data)
        update_id+=1

    while True:
        message = input() 
        result = requests.get('{}{}/sendMessage?chat_id={}&text={}'.format(URL,BOT_TOKEN, CHAT, message))
        parola = requests.get()
        print(result.text)


def main():
    send_message()

if __name__ == '__main__':
    main()
