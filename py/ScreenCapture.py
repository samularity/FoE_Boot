#!/usr/bin/env python3
import pyautogui, sys, time

def GetTheMoney():
    "Tis function grabs a frame and click below the coin"
    pos = pyautogui.locateOnScreen('geld.png') 
    if (pos != None):
        x, y = pyautogui.center(pos)       
        print(time.ctime(), 'Found coin @ x:', x , ' y:', y)
        pyautogui.click(x, y+55)
    return

def GetTheResources():
    "Tis function grabs a frame and click below the hammer"
    pos = pyautogui.locateOnScreen('hammer.png') 
    if (pos != None):
        x, y = pyautogui.center(pos)       
        print(time.ctime(), 'Found hammer @ x:', x , ' y:', y)
        pyautogui.click(x, y+100)
    return

def GetTheArmy():
    "Tis function grabs a frame and click below the hammer"
    pos = pyautogui.locateOnScreen('schwert.png') 
    if (pos != None):
        x, y = pyautogui.center(pos)       
        print(time.ctime(), 'Found sword @ x:', x , ' y:', y)
        pyautogui.click(x, y+75)
    return

def GetShitDone():
    "Tis function looks after the moon and get shit done"
    pos = pyautogui.locateOnScreen('moon.png') 
    if (pos != None):
        x, y = pyautogui.center(pos)       
        print(time.ctime(), 'Found moon @ x:', x , ' y:', y)
        pyautogui.click(x, y+75)
        time.sleep( 1.5 )
        pos = pyautogui.locateOnScreen('horn.png') 
        if (pos != None):
            x, y = pyautogui.center(pos)       
            print(time.ctime(), 'make horn @ x:', x , ' y:', y)
            pyautogui.click(x, y+75)
            return  
        pos = pyautogui.locateOnScreen('pot.png') 
        if (pos != None):
            x, y = pyautogui.center(pos)       
            print(time.ctime(), 'make pot @ x:', x , ' y:', y)
            pyautogui.click(x, y+75)
            return  
        pos = pyautogui.locateOnScreen('kohl.png') 
        if (pos != None):
            x, y = pyautogui.center(pos)       
            print(time.ctime(), 'make kohl @ x:', x , ' y:', y)
            pyautogui.click(x, y)
            return 
        pos = pyautogui.locateOnScreen('close.png') 
        if (pos != None):
            x, y = pyautogui.center(pos)       
            print(time.ctime(), 'exit @ x:', x , ' y:', y)
            pyautogui.click(x, y)
            return                   
    return

def main():
    print(time.ctime(), 'Program started.')
    # Note where the bot's window is.
    input('Move mouse over bot window and press Enter.')
    botWindow = pyautogui.position()
    while True:
        GetTheMoney()
        GetTheMoney()
        GetTheResources()
        GetTheArmy()
        GetShitDone()   
        
        
if __name__ == "__main__":
    sys.exit(main())