package core.basesyntax;

import java.util.List;
import java.util.Objects;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private int size = 0;
    private Node<T> head;
    private Node<T> tail;

    private static class Node<T> {
        private T value;
        private Node<T> prev;
        private Node<T> next;

        public Node(T value) {
            this.value = value;
        }

        public Node(Node<T> prev, T value, Node<T> next) {
            this.value = value;
            this.prev = prev;
            this.next = next;
        }
    }

    @Override
    public void add(T value) {
        Node<T> newNode = new Node<>(value);
        if (head == null) {
            head = newNode;
        } else {
            tail.next = newNode;
            newNode.prev = tail;
        }
        tail = newNode;
        size++;
    }

    @Override
    public void add(T value, int index) {
        checkIndexForAdd(index);
        if (index == size) {
            add(value);
            return;
        }
        if (index == 0) {
            Node<T> newNode = new Node<>(null, value, head);
            if (head != null) {
                head.prev = newNode;
            }
            head = newNode;
            if (tail == null) {
                tail = newNode;
            }
        } else {
            Node<T> current = head;
            for (int i = 0; i < index; i++) {
                current = current.next;
            }
            Node<T> newNode = new Node<>(current.prev, value, current);
            current.prev.next = newNode;
            current.prev = newNode;
        }
        size++;
    }

    @Override
    public void addAll(List<T> list) {
        for (T listValue : list) {
            add(listValue);
        }
    }

    @Override
    public T get(int index) {
        checkIndexFor(index);
        Node<T> current = head;
        for (int i = 0; i < index; i++) {
            current = current.next;
        }
        return current.value;
    }

    @Override
    public T set(T value, int index) {
        checkIndexFor(index);
        if (index == 0) {
            T oldValue = head.value;
            head.value = value;
            return oldValue;
        }
        if (index == size - 1) {
            T oldValue = tail.value;
            tail.value = value;
            return oldValue;
        } else {
            Node<T> current = head;
            for (int i = 0; i < index; i++) {
                current = current.next;
            }
            T oldValue = current.value;
            current.value = value;
            return oldValue;
        }
    }

    @Override
    public T remove(int index) {
        checkIndexFor(index);
        if (index == 0) {
            if (size == 1) {
                size--;
                T oldValue = head.value;
                head = null;
                tail = null;
                return oldValue;
            } else {
                size--;
                T oldValue = head.value;
                head = head.next;
                head.prev = null;
                return oldValue;
            }
        }
        if (index == size - 1) {
            size--;
            T oldValue = tail.value;
            tail = tail.prev;
            tail.next = null;
            return oldValue;
        } else {
            Node<T> current = head;
            for (int i = 0; i < index; i++) {
                current = current.next;
            }
            size--;
            T oldValue = current.value;
            current.prev.next = current.next;
            current.next.prev = current.prev;
            return oldValue;
        }
    }

    @Override
    public boolean remove(T object) {
        if (head == null) {
            return false;
        }

        Node<T> current = head;
        while (current != null) {
            if (Objects.equals(current.value, object)) {
                if (current == head) {
                    if (size == 1) {
                        head = null;
                        tail = null;
                        size--;
                        return true;
                    } else {
                        head = head.next;
                        head.prev = null;
                        size--;
                        return true;
                    }
                }
                if (current == tail) {
                    tail = tail.prev;
                    tail.next = null;
                    size--;
                    return true;
                }
                current.prev.next = current.next;
                current.next.prev = current.prev;
                size--;
                return true;
            }
            current = current.next;
        }
        return false;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return head == null;
    }

    public void checkIndexForAdd(int index) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException();
        }
    }

    public void checkIndexFor(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException();
        }
    }
}
