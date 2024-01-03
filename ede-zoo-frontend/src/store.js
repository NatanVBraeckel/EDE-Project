import { atom } from 'recoil';

export const jwtState = atom({
    key: 'jwtState',
    default: '',
})

export const userState = atom({
    key: 'userState',
    default: {},
})