import request from '@/utils/request'

export const listTask = (search) =>
    request({
        url: '/rest/task',
        method: 'get',
        params: search
    })

export const countTask = (search) =>
    request({
        url: '/rest/task/count',
        method: 'get',
        params: search
    })

export const delTaskAttachment = (taskId, attachmentId) =>
    request({
        url: '/rest/task/' + taskId + '/attachment/' + attachmentId,
        method: 'DELETE'
    })  